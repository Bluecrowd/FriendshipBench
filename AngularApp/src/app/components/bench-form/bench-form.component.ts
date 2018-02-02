import {Component, Input, OnInit} from '@angular/core';

import { Bench } from '../../models/bench';
import {BenchesComponent} from '../benches/benches.component';
import { BenchesService} from '../../services/benches.service';
import {MapsAPILoader} from "@agm/core";
declare var google: any;
  @Component({
    selector: 'app-bench-form',
    templateUrl: './bench-form.component.html',
    styleUrls: ['./bench-form.component.css']
  })
  export class BenchFormComponent implements OnInit {
    @Input() addBenchToggle: false;

    long: number;
    lati: number;
    submitted = false;

    model: Object = {};

    onSubmit() { this.submitted = true; }

    // TODO: Remove this when it works

    private geocoder: any;
    constructor(
      private benchesComponent: BenchesComponent,
      private benchesService: BenchesService,
      private mapsAPILoader: MapsAPILoader
    ) {

    }

    ngOnInit() {
    }

    mapClicked($event:any) {
        console.log($event.coords.lat);
        this.lati = $event.coords.lat;
        this.long = $event.coords.lng;
      this.mapsAPILoader.load().then(() => {
        console.log('google script loaded');
        this.geocoder = new google.maps.Geocoder();
      }).then(() => {
        var LatLng = {lat: this.lati, lng: this.long};
        this.geocoder.geocode({'location': LatLng}, (results, status) => {
          if (status == 'OK') {
            console.log("gelukt!")
          } else {
            alert('Geocode was not successful for the following reason: ' + status);
          }
        });
      });
    }

    closeAddBench(): void {
      this.addBenchToggle = false;
      this.benchesComponent.addBenchToggle = false;
      this.model = {};
    }

    add(): void {
      if (this.model != null) {
        this.benchesService.addBench(this.model as Bench)
          .subscribe(bench => {this.benchesComponent.benches.push(bench);
          });
      }
    }

  }
