import { Component, OnInit, AfterViewInit } from '@angular/core';
import {Bench} from '../../models/bench';
import {BenchesService} from '../../services/benches.service';
import { MapsAPILoader} from "@agm/core";
declare var google: any;
declare var $: any;

@Component({
  selector: 'app-benches',
  templateUrl: './benches.component.html',
  styleUrls: ['./benches.component.css']
})
export class BenchesComponent implements OnInit, AfterViewInit {
  benches: Bench[];
  lat: number = 52;
  lng: number = 10;
  public lbl: string;

  markers: marker[] = [
    {
      name: 'company one',
    }
  ]

  public selectedBench: Bench;
  public addBenchToggle = false;

  private geocoder: any;

  constructor(
    private benchesService: BenchesService,
    private mapsAPILoader: MapsAPILoader,
    ) {

    this.mapsAPILoader.load().then(() => {
      console.log('google script loaded');
      this.geocoder = new google.maps.Geocoder();
    }).then(() => {
      const address = "Leeuwarden";
      this.geocoder.geocode( { 'address': address}, (results, status) => {
        if (status == 'OK') {
          this.lat = results[0].geometry.location.lat();
          this.lng = results[0].geometry.location.lng();
          console.log(results[0].geometry.location.lat());
          console.log(this.lat + ' ' + this.lng);
        } else {
          alert('Geocode was not successful for the following reason: ' + status);
        }
      });
    });

  }

  ngOnInit() {
    this.getBenches();
    // this.benchesService.getLatLan("Leeuwarden");
  }

  ngAfterViewInit() {

  }

  toggleAddBench(): void {
    this.addBenchToggle = !this.addBenchToggle;
  }

  getBenches(): void {
    this.benchesService.getBenches()

      .subscribe(benches => {
        this.benches = benches;
        //this.lbl = benches[0].streetname;
      });
  }

  delete(bench: Bench): void {
    this.benches = this.benches.filter(h => h !== bench);
    this.benchesService.deleteBench(bench).subscribe();
  }

  toggleBench(bench: Bench): void {
    console.log(bench.streetname);
    this.selectedBench = bench;
  }
}
interface marker {

}
