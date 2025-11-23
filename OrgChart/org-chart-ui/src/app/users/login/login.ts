import {ChangeDetectionStrategy, Component, effect, inject} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatInputModule} from '@angular/material/input';
import {MatButtonModule} from '@angular/material/button';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {Router} from '@angular/router';
import {UserStore} from '../user.store/user.store';
import {User} from '../user-logged-on.interface';
import {MatIconModule} from '@angular/material/icon';
import {MatSelectModule} from '@angular/material/select';


@Component({
  selector: 'wt-login',
  imports: [CommonModule, FormsModule,
    MatCardModule, MatInputModule, MatButtonModule,
    MatProgressSpinnerModule, MatSelectModule, MatIconModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class Login {
  readonly store = inject(UserStore);
  private router = inject(Router);

  id = '';

  constructor() {
    effect(() => {
      if (this.store.isLoggedIn()) {
        console.log('User is logged in, redirecting...');
        this.router.navigate(['/org-chart']);
      }
    });
  }

  // Helper method called when dropdown changes
  fillCredentials(user: User) {
    this.id = user.id;
  }

  onSubmit() {
    const selectedUser: User | undefined = this.demoUsers.find((user: User) => user.id === this.id);

    this.store.login(selectedUser ? selectedUser : this.demoUsers[0]);
  }

  demoUsers: User[] = [
    {
      id: '123',
      username: 'Admin (CEO)',
      role: 'ADMIN'
    },
    {
      id: '456',
      username: 'John (Project X)',
      role: 'USER'
    },
    {
      id: '789',
      username: 'Alice (Marketing)',
      role: 'USER'
    }
  ];
}
