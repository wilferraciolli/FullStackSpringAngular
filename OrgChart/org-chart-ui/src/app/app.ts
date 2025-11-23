import {ChangeDetectionStrategy, Component, inject, signal} from '@angular/core';
import {Router, RouterOutlet} from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatToolbarModule} from '@angular/material/toolbar';
import {UserStore} from './users/user.store/user.store';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatToolbarModule, MatButtonModule, MatIconModule],
  templateUrl: './app.html',
  styleUrl: './app.scss',
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class App {
  protected readonly title = signal('Org chart');

  readonly store = inject(UserStore);
  private router = inject(Router);

  protected login() {
    this.router.navigate(['/login']);
  }

  protected logout(): void {
    this.store.logout();
  }
}
