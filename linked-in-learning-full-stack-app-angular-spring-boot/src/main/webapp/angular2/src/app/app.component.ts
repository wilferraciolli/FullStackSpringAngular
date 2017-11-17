import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {Http, Headers, RequestOptions, Response} from '@angular/http';
import {Observable, operators} from "rxjs/Rx";
import "rxjs/add/operator/map";
import "rxjs/add/operator/catch"

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  /**
   * Default constructor
   */
  constructor(private http: Http) {
  }

  private baseUrl: string = 'http://localhost:8080';
  public submitted: boolean;

  roomsearch: FormGroup;
  rooms: Room[];
  request: ReserveRoomRequest;
  currentCheckInVal: string;
  currentCheckOutVal: string;

  ngOnInit() {
    this.roomsearch = new FormGroup({
      checkin: new FormControl(''),
      checkout: new FormControl('')
    });

    //constant to hold the form group.value changes.
    const roomsearchValueChanges$ = this.roomsearch.valueChanges;

    //change the value of checkin and checkout after every change on the form 'roomsearch'
    roomsearchValueChanges$.subscribe(valChange => {
      this.currentCheckInVal = valChange.checkin;
      this.currentCheckOutVal = valChange.checkout;
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
    this.request = new ReserveRoomRequest(value, this.currentCheckInVal, this.currentCheckOutVal);

    this.createReservation(this.request);
  }

  /**
   * Get all bookings
   * @returns {Observable<Room[]>}
   */
  getAll(): Observable<Room[]> {
    return this.http.get(this.baseUrl + '/room/reservation/v1?checkin=' + this.currentCheckInVal + '-18&checkout=' + this.currentCheckOutVal)
      .map(this.mapRoom);
  }

  /**
   * Metho to build a request body to send to an API to create a resource
   * @param {ReserverRoomRequest} body
   */
  createReservation(body: ReserveRoomRequest) {
    let bodyString = JSON.stringify(body);// Stringify payload
    let headers = new Headers({'Content-Type': 'application/json'});// ... Set content type to JSON
    let option = new RequestOptions({headers: headers});// Create a request option

    this.http.post(this.baseUrl + '/room/reservation/v1', body, option)
      .subscribe(res => console.log(res));
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

export class ReserveRoomRequest {
  roomId: string;
  checkin: string;
  checkout: string;

  constructor(roomId: string, checkin: string, checkout: string) {
    this.roomId = roomId;
    this.checkin = checkin;
    this.checkout = checkout;
  }
}
