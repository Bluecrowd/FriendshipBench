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

@Component({
  selector: 'app-appointments-form',
  templateUrl: './appointments-form.component.html',
  styleUrls: ['./appointments-form.component.css']
})
export class AppointmentsFormComponent implements OnInit {
  @Input() addAppointmentToggle: false;

  model: Object;

  benches: Bench[];

  myClients: Client[];

  currentHw: AccountDetails;

  // TODO: Remove this when it works
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(
    private appointmentsComponent: AppointmentsComponent,
    private appointmentsService: AppointmentsService,
    private benchesService: BenchesService,
    private clientsService: ClientsService,
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

  ngOnInit() {
    this.getBenches();
    this.getMyClients();
    this.getCurrentHw();
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
