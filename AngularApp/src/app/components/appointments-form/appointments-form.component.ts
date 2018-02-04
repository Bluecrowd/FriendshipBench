import {Component, Input, OnInit} from '@angular/core';
import {AppointmentsComponent} from '../appointments/appointments.component';
import {AppointmentsService} from '../../services/appointments.service';
import {Bench} from '../../models/bench';
import {BenchesService} from '../../services/benches.service';
import {Client} from '../../models/client';
import {ClientsService} from '../../services/clients.service';
import {Healthworker} from '../../models/healthworker';
import {AuthenticationService} from '../../services/authentication.service';
import {Constants} from '../../Constants';
import {AccountDetails} from '../../models/accountDetails';
import {AppointmentInterface} from '../../models/appointment-interface';
import {AddAppointment} from '../../models/add-appointment';
import {NgForm} from '@angular/forms';
import {Question} from '../../models/question';
import {Appointment} from '../../models/appointment';
import {MapsAPILoader} from "@agm/core";
declare var google: any;
@Component({
  selector: 'app-appointments-form',
  templateUrl: './appointments-form.component.html',
  styleUrls: ['./appointments-form.component.css']
})
export class AppointmentsFormComponent implements OnInit {
  @Input() addAppointmentToggle: false;

  model: Object;
  markerBench: Bench;
  myText: string = "hoiiiii";
  lat: number;
  lng: number;
  address: string;
  benches: Bench[];

  myClients: Client[];
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
  currentHw: AccountDetails;
  private geocoder: any;

  constructor(
    private appointmentsComponent: AppointmentsComponent,
    private appointmentsService: AppointmentsService,
    private benchesService: BenchesService,
    private clientsService: ClientsService,
    private mapsAPILoader: MapsAPILoader,
  ) {
    this.model = {
      'timestamp': '',
      bench: {
        id: null
      },
      client: {
        id: null
      },
      healthWorker: {
        id: null
      }
    };
  }

  // TODO: Remove this when it works
  get diagnostic() { return JSON.stringify(this.model); }

  ngOnInit() {
    this.getBenches();
    this.getMyClients();
    this.getCurrentHw();
    this.showMap();
  }

  showMap(): void{

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
  markerClick(marker: any): void {
      this.model["bench"] = {id: marker.label};
  }
  getCurrentHw(): void {
    this.appointmentsService.requestAccountDetails()
      .subscribe( hw => this.currentHw = hw);
  }

  getMyClients(): void {
    this.clientsService.getClients()
      .subscribe( clients => this.myClients = clients);

  }

  getBenches(): void {
    this.benchesService.getBenches()
      .subscribe( benches => this.benches = benches );
  }

  add(): void {
    if (this.model != null) {
      this.appointmentsService.addAppointment(this.model as Appointment)
        .subscribe(appointment => {this.appointmentsComponent.appointments.push(appointment);
        });
    }
    this.closeAddAppointment();
    this.appointmentsComponent.refreshAppointments();
  }

  closeAddAppointment(): void {
    this.addAppointmentToggle = false;
    this.appointmentsComponent.addAppointmentsToggle = false;
    this.model = {
      'timestamp': '',
      bench: {
        id: null
      },
      client: {
        id: null
      },
      healthWorker: {
        id: null
      }
    };
  }

}
interface marker {

}
