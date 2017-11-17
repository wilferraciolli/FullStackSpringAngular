import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Http, Response} from '@angular/http';
import {Observable, operators} from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  private baseUrl: string = 'http://localhost:8080';
  public submitted: boolean;
  roomsearch: FormGroup;
  rooms: Room[];

  /**
   * Default constructor
   */
  constructor(private http: Http) {
  }

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

  }

  /**
   * Method to track whether the form was submitted
   * @param {RoomSearch} value
   * @param {boolean} valid
   */
  onSubmit({value, valid}: { value: RoomSearch, valid: boolean }) {
    console.log(value);
    this.getAll()
      .subscribe(rooms => this.rooms = rooms,
        err => {
          console.log(err);
        });
  }

  /**
   * Method to reserve a room by id.
   * @param {string} value
   */
  reserveRoom(value: string) {
    console.log("Room id for reservation" + value);
  }

  /**
   * Get all bookings
   * @returns {Observable<Room[]>}
   */
  getAll(): Observable<Room[]> {
    return this.http.get(this.baseUrl + '/room/reservation/v1?checkin=2017-03-18&checkout=2017-03-25')
      .map(this.mapRoom);
  }

  /**
   * Method to map the content section of a response.
   * @param {Response} response
   * @returns {Room[]}
   */
  mapRoom(response: Response): Room[] {
    //get the response, extract as Json and grab he content
    return response.json().content;
  }

}

export interface RoomSearch {
  checkin: string;
  checkout: string;
}

export interface Room {
  id: string;
  roomNumber: string;
  price: string;
  links: string;
}
