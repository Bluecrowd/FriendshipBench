import {Component, Input, OnInit} from '@angular/core';
import {AppointmentsService} from '../../services/appointments.service';
import {Appointment} from '../../models/appointment';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {
  appointments: Appointment[];

  public addAppointmentsToggle = false;

  constructor(
    private appointmentsService: AppointmentsService,
  ) { }

  ngOnInit() {
    this.getAppointments();
  }

  toggleAddAppointment(): void {
    this.addAppointmentsToggle = !this.addAppointmentsToggle;
  }

  cancelAppointment(app: Appointment): void {
      app.status = 'CANCELLED';
    this.appointmentsService.cancelAppointment(app.id).subscribe();
  }

  getAppointments(): void {
    this.appointmentsService.getAppointments()
      .subscribe(appointments => this.appointments = appointments);
  }

}
