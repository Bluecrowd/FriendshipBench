import { Component, OnInit } from '@angular/core';
import {GoogleMapsService} from "../../services/google-maps.service";
import {GoogleMaps} from "../../models/googleMaps";

@Component({
  selector: 'app-google-maps',
  templateUrl: './google-maps.component.html',
  styleUrls: ['./google-maps.component.css']
})
export class GoogleMapsComponent implements OnInit {
  googleMaps: GoogleMaps[];


  constructor(private googleMapsService: GoogleMapsService) { }

  ngOnInit() {
  }

  getGoogleMaps(): void {
    this.googleMapsService.getGoogleMaps()
      .subscribe(googleMaps => this.googleMaps = googleMaps);
  }
}
