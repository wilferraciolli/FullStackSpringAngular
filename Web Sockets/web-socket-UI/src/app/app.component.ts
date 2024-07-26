import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterOutlet } from '@angular/router';
import { Message, WebSocketService } from './services/web-socket.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, FormsModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})
export class AppComponent {
  title: string = 'web-socket-UI';
  content: string = '';
  received: Message[] = [];
  sent: Message[] = [];

  constructor(private websocketService: WebSocketService) {
    websocketService.messages.subscribe(msg => {
      this.received.push(msg);
      console.log('Response from websocket: ' + msg);
    });
  }

  sendMsg() {
    let message = {
      message: '',
      source: ''
    };
    message.source = 'localhost';
    message.message = this.content;

    this.sent.push(message);
    this.websocketService.messages.next(message);
  }
}
