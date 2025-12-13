import { Component } from '@angular/core';
import {Pannable} from '../../directives/pannable.directive';

@Component({
  selector: 'wt-canvas-wrapper',
  imports: [
    Pannable
  ],
  templateUrl: './canvas-wrapper.html',
  styleUrl: './canvas-wrapper.scss',
})
export class CanvasWrapper {

}
