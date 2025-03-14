import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {io, Socket} from 'socket.io-client';
import {Message} from '../interfaces/message.interface';
import {Room} from '../interfaces/room.interface';
import {RoomAcknowledge} from '../interfaces/room-acknowledge.interface';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  private socket: Socket;
  private url = 'http://localhost:3000';

  constructor() {
    this.socket = io(this.url);
  }

  // Connect to socket server
  public connect(): void {
    this.socket = io(this.url);
    console.log('Connected to socket server');
  }

  // Disconnect from socket server
  public disconnect(): void {
    if (this.socket) {
      this.socket.disconnect();
    }
  }

  // Send a message to the server
  public emit(eventName: string, data: Message): void {
    this.socket.emit(eventName, data);
  }

  // Listen for events from the server
 public on<T>(eventName: string): Observable<T> {
    return new Observable<T>(observer => {
      this.socket.on(eventName, (data: T) => {
        observer.next(data);
      });

      return () => {
        this.socket.off(eventName);
      };
    });
  }

  public async joinRoom(data: Room): Promise<RoomAcknowledge> {
    return this._roomAction('join-room', data);
  }

  public async leaveRoom(data: Room): Promise<RoomAcknowledge> {
    return this._roomAction('leave-room', data);
  }

  private async _roomAction(eventName: string, data: Room): Promise<RoomAcknowledge> {
    const response: RoomAcknowledge = await this.socket.emitWithAck(eventName, data);

    return response;
  }
}

