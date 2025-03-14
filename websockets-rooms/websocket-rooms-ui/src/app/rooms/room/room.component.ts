import {Component, computed, OnDestroy, OnInit, Signal, signal, WritableSignal} from '@angular/core';
import {MatDrawer, MatDrawerContainer, MatDrawerContent} from '@angular/material/sidenav';
import {MatBadge} from '@angular/material/badge';
import {MatButton} from '@angular/material/button';
import {Message} from '../../interfaces/message.interface';
import {FormControl, FormsModule, ReactiveFormsModule} from '@angular/forms';
import {SocketService} from '../../services/socket.service';
import {SimpleMessage} from '../../interfaces/sinple-message.interface';
import {DatePipe, NgForOf} from '@angular/common';
import {RoomAcknowledge} from '../../interfaces/room-acknowledge.interface';

@Component({
  selector: 'app-room',
  imports: [
    MatDrawerContainer,
    MatDrawerContent,
    MatDrawer,
    MatBadge,
    MatButton,
    DatePipe,
    FormsModule,
    NgForOf,
    ReactiveFormsModule
  ],
  templateUrl: './room.component.html',
  styleUrl: './room.component.scss'
})
export class RoomComponent implements OnInit, OnDestroy {
  messages: Message[] = [];
  messageInput = new FormControl('');
  private _clientId: WritableSignal<string> = signal('');
  private _roomId: WritableSignal<number> = signal(1);

  public clientId: Signal<string> = computed(() => this._clientId());
  public roomId: Signal<number> = computed(() => this._roomId());

  constructor(private socketService: SocketService) {
  }

  public async ngOnInit(): Promise<void> {
    this.socketService.connect();

    this.socketService
      .on<SimpleMessage>('client-connected')
      .subscribe((message: SimpleMessage) => {
        this._clientId.set(message.clientId);
      });

    // Important: Join the current room after connection
    const response: RoomAcknowledge = await this.socketService.joinRoom({resourceId: this._roomId().toString()});
    console.log('room ack ', response)

    // Listen for incoming messages to the room
    this.socketService
      .on<Message>('room-message-reply')
      .subscribe((message: Message) => {
        console.log('received message resource-update ', message)
        this.messages.push(message);
      });
  }

  public ngOnDestroy(): void {
    this.socketService.disconnect();
  }

  public sendMessage(): void {
    // send message to room, but only clients subscribed to room will receive it
    if (this.messageInput.value && this.messageInput.value.trim() !== '') {
      const message: Message = {
        id: 'messageId',
        clientId: this._clientId(),
        feature: 'checkIns',
        subFeature: 'checkIn',
        resourceId: this._roomId().toString(),
        messageType: 'comment-added',
        message: this.messageInput.value.trim(),
        omitSender: false,
        timestamp: new Date()
      };

      this.socketService.emit('rooms', message);
      this.messageInput.setValue('');
    }
  }

  public async navigateToRoom(roomNumber: number): Promise<void> {
    // Important: unjoin old room and join new room after connection
    const [leaveResponse, joinResponse] = await Promise.all([
      this.socketService.leaveRoom({resourceId: this._roomId().toString()}),
      this.socketService.joinRoom({resourceId: roomNumber.toString()})
    ]);
    console.log('Left room:', leaveResponse);
    console.log('Joined room:', joinResponse);

    // update to the latest room
    this._roomId.set(roomNumber);
  }
}
