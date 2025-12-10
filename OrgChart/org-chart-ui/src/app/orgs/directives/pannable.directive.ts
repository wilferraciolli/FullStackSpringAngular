import {Directive, ElementRef, HostListener, Renderer2} from '@angular/core';

@Directive({
  selector: '[wtPannable]',
  standalone: true,
})
export class Pannable {
  private isMouseDown: boolean = false;
  private isSpacebarHeld: boolean = false;
  private isPanning: boolean = false;

  private startX: number = 0;
  private startY: number = 0;
  private currentX: number = 0;
  private currentY: number = 0;

  private lastMouseX: number = 0;
  private lastMouseY: number = 0;

  constructor(private el: ElementRef, private renderer: Renderer2) {
    // Initial style setup
    this.renderer.setStyle(this.el.nativeElement, 'transform-origin', '0 0');
    this.renderer.setStyle(this.el.nativeElement, 'position', 'absolute'); // Allow free movement
    this._updateCursor();
  }

  @HostListener('mousedown', ['$event'])
  onMouseDown(event: MouseEvent): void {
    this.isMouseDown = true;
    this._updateCursor();

    if (this.isSpacebarHeld || (!this.isSpacebarHeld && event.button === 0)) {
      this._startPanning(event);
    }
  }

  @HostListener('document:mousemove', ['$event'])
  onMouseMove(event: MouseEvent): void {
    this.lastMouseX = event.clientX;
    this.lastMouseY = event.clientY;

    if (!this.isPanning) {
      return;
    }

    event.preventDefault();

    // calculate new position
    this.currentX = event.clientX - this.startX;
    this.currentY = event.clientY - this.startY;

    // apply transform
    this.renderer.setStyle(
      this.el.nativeElement,
      'transform',
      `translate3d(${this.currentX}px, ${this.currentY}px, 0)`);
  }

  @HostListener('document:mouseup')
  // @HostListener('document:mouseleave')
  onMouseUp(): void {
    this.isMouseDown = false;
    this._stopPanning();
    this._updateCursor();
  }

  @HostListener('document:keydown', ['$event'])
  onKeyDown(event: KeyboardEvent): void {
    if (event.code === 'Space' && !this.isSpacebarHeld) {
      event.preventDefault();

      this.isSpacebarHeld = true;
      this._updateCursor();

      // If mouse is already down, start panning immediately
      if (this.isMouseDown) {
        this._startPanning({
          clientX: this.lastMouseX,
          clientY: this.lastMouseY,
          button: 0
        } as MouseEvent);
      }
    }
  }

  @HostListener('document:keyup', ['$event'])
  onKeyUp(event: KeyboardEvent) {
    if (event.code === 'Space') {
      this.isSpacebarHeld = false;
      this._stopPanning();
      this._updateCursor();
    }
  }

  // --- Cursor Management ---
  private _updateCursor(): void {
    let cursorStyle = 'default';
    if (this.isPanning) {
      cursorStyle = 'grabbing';
    } else if (this.isSpacebarHeld || this.isMouseDown) {
      cursorStyle = 'grab';
    }
    this.renderer.setStyle(
      this.el.nativeElement,
      'cursor',
      cursorStyle);
  }

  private _startPanning(event: MouseEvent): void {
    // Only start panning if spacebar or mouse is pressed
    if (this.isSpacebarHeld || this.isMouseDown) {
      this.isPanning = true;
      this._updateCursor();
      // Calculate the start offset relative to current translation
      this.startX = event.clientX - this.currentX;
      this.startY = event.clientY - this.currentY;
    }
  }

  private _stopPanning(): void {
    this.isPanning = false;
  }
}
