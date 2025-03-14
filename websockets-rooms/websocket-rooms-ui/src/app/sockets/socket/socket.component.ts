import {Component, OnDestroy, OnInit, signal, WritableSignal} from '@angular/core';
import {FormControl, ReactiveFormsModule} from '@angular/forms';
import {Message} from '../../interfaces/message.interface';
import {SocketService} from '../../services/socket.service';
import {DatePipe, NgForOf} from '@angular/common';
import {SimpleMessage} from '../../interfaces/sinple-message.interface';

@Component({
  selector: 'app-socket',
  imports: [
    ReactiveFormsModule,
    DatePipe,
    NgForOf
  ],
  templateUrl: './socket.component.html',
  styleUrl: './socket.component.scss'
})
export class SocketComponent implements OnInit, OnDestroy {
  messages: Message[] = [];
  messageInput = new FormControl('');
  username = 'User_' + Math.floor(Math.random() * 1000);
  private _clientId: WritableSignal<string> = signal('');

  constructor(private socketService: SocketService) {
  }

  public ngOnInit(): void {
    this.socketService.connect();

    // Listen for incoming messages
    this.socketService
      .on<Message>('reply')
      .subscribe((message: Message) => {
        console.log('Received message ', message);
        this.messages.push({...message, timestamp: new Date});
      });

    this.socketService
      .on<SimpleMessage>('client-connected')
      .subscribe((message: SimpleMessage) => {
        this._clientId.set(message.clientId);
        // this.messages.push({
        //   id: 'SOME ID',
        //   clientId: message.clientId,
        //   feature: 'SOME ID',
        //   subFeature: 'SOME ID',
        //   resourceId: 'resourceId',
        //   message: message.message,
        //   timestamp: new Date,
        // });
      });

    // Listen for connection events (optional)
    this.socketService.on<string>('reply').subscribe(userId => {
      console.log(`User connected: ${userId}`);
    });
  }

  public ngOnDestroy(): void {
    this.socketService.disconnect();
  }

  public sendMessage(): void {
    if (this.messageInput.value && this.messageInput.value.trim() !== '') {
      const message: Message = {
        id: 'messageId',
        clientId: this._clientId(),
        feature: 'checkIns',
        subFeature: 'checkIn',
        resourceId: 'resourceId',
        messageType: 'commend-added',
        message: this.messageInput.value.trim(),
        ommitSender: false,
        timestamp: new Date()
      };

      this.socketService.emit('channels', message);
      this.messageInput.setValue('');
    }
  }
}
