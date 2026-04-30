import { Component, signal, computed, WritableSignal, Signal } from '@angular/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatListModule } from '@angular/material/list';
import { FormsModule } from '@angular/forms';
import { categories, QueryCategory } from '../../interfaces/category.interface';
import { queries, SavedQuery } from '../../interfaces/query.interface';

@Component({
  selector: 'app-query-viewer',
  imports: [
    FormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatIconModule,
    MatButtonModule,
    MatCardModule,
    MatListModule,
  ],
  templateUrl: './query-viewer.html',
  styleUrl: './query-viewer.scss',
})
export class QueryViewer {
  protected searchTerm: WritableSignal<string> = signal('');
  protected selectedCategory: WritableSignal<QueryCategory | null> = signal<QueryCategory | null>(
    null,
  );

  protected readonly categories: QueryCategory[] = categories;

  protected filteredQueries: Signal<SavedQuery[]> = computed(() => {
    const term: string = this.searchTerm().toLowerCase();
    const cat: QueryCategory | null = this.selectedCategory();

    return queries.filter((q) => {
      const matchesCat = cat ? q.categoryId === cat.id : true;
      const matchesTerm = term ? q.name.toLowerCase().includes(term) : true;

      return matchesCat && matchesTerm;
    });
  });

  protected selectCategory(cat: QueryCategory): void {
    this.selectedCategory.set(this.selectedCategory()?.id === cat.id ? null : cat);
  }

  protected onSearch(value: string): void {
    this.searchTerm.set(value);
  }
}
