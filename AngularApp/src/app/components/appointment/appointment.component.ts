import {Component, Input, OnInit} from '@angular/core';
import {Appointment} from '../../models/appointment';
import {AppointmentsService} from '../../services/appointments.service';

@Component({
  selector: 'app-appointment',
  templateUrl: './appointment.component.html',
  styleUrls: ['./appointment.component.css']
})
export class AppointmentComponent implements OnInit {

  @Input() app: Appointment;

  constructor(
    private appointmentsComponent: AppointmentComponent,
    private appointmentsService: AppointmentsService
  ) { }

  ngOnInit() {
  }

  cancelAppointment(): void {
    this.app.status = 'cancelled';
    this.appointmentsService.cancelAppointment(this.app.id).subscribe();
  }

}
