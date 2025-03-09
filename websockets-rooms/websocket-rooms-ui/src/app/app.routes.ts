import { Routes } from '@angular/router';
import {SocketComponent} from './sockets/socket/socket.component';

export const routes: Routes = [
  { path: '', component: SocketComponent },
  { path: '**', redirectTo: '' }
];
