import { Component, OnInit } from '@angular/core';
import {AppointmentsService} from '../../services/appointments.service';

@Component({
  selector: 'app-appointments',
  templateUrl: './appointments.component.html',
  styleUrls: ['./appointments.component.css']
})
export class AppointmentsComponent implements OnInit {

  constructor(
    private appointmentsService: AppointmentsService,
  ) { }

  ngOnInit() {
  }

}
