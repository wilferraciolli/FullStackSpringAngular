import { Component } from '@angular/core';
import { MatTabsModule } from '@angular/material/tabs';
import { QueryViewer } from '../query-viewer/query-viewer';
import { QueryBuilder } from '../query-builder/query-builder';

@Component({
  selector: 'app-query-manager',
  imports: [MatTabsModule, QueryViewer, QueryBuilder],
  templateUrl: './query-manager.html',
  styleUrl: './query-manager.scss',
})
export class QueryManager {}
