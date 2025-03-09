import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { io, Socket } from 'socket.io-client';
import {Message} from '../interfaces/message.interface';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  private socket: Socket;
  private url = 'http://localhost:3000'; // your Socket.IO server URL

  constructor() {
    this.socket = io(this.url);
  }

  // Connect to socket server
  connect(): void {
    this.socket = io(this.url);
    console.log('Connected to socket server');
  }

  // Disconnect from socket server
  disconnect(): void {
    if (this.socket) {
      this.socket.disconnect();
    }
  }

  // Send a message to the server
  emit(eventName: string, data: Message): void {
    this.socket.emit(eventName, data);
  }

  // Listen for events from the server
  on<T>(eventName: string): Observable<T> {
    return new Observable<T>(observer => {
      this.socket.on(eventName, (data: T) => {
        observer.next(data);
      });

      return () => {
        this.socket.off(eventName);
      };
    });
  }
}

