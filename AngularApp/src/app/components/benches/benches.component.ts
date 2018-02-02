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
  lat: number;
  lng: number;
  address: string;
  public lbl: string;

  markers: marker[] = [
    {
      lat: -90.673858,
      lng: 7.815982,
      label: 'i',
      draggable: true,
      streetname: "hoi",
      housenumber: "doei",
      district: "Zwolle",
      provice: "Overeisel",
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

        this.benchesService.getBenches()

          .subscribe(benches => {
            var i;
            var len = benches.length;
            for (i = 0; i < len; i++) {
              const index = i;
            const ad = benches[index];
            this.benches = benches;
            this.address = ad.streetname;
            this.address += " " + ad.housenumber;
            this.address += " " + ad.district;




      const address = this.address;
      this.geocoder.geocode({'address': address}, (results, status) => {
        if (status == 'OK') {
          var lat = results[0].geometry.location.lat();
          var lng = results[0].geometry.location.lng();
          var mark =
            {
              lat: lat,
              lng: lng,
              label: ''+ad.id+'',
              draggable: true,
              streetname: address,
              housenumber: "doei",
              district: "Zwolle",
              provice: "Overeisel",
            };
          this.markers.push(mark);
          this.lat = lat;
          this.lng = lng;

        } else {
          alert('Geocode was not successful for the following reason: ' + status);
        }
      });
            }
          });

    });

  }

  ngOnInit() {
    this.getBenches();
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
