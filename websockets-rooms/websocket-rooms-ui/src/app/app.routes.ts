import {Routes} from '@angular/router';
import {RoomComponent} from './rooms/room/room.component';
import {SocketComponent} from './sockets/socket/socket.component';

export const routes: Routes = [
  {path: '', component: RoomComponent},
  {path: 'socket', component: SocketComponent},
  {path: '**', redirectTo: ''}
];
