import { Component, OnInit } from '@angular/core';
import {HealthworkersService} from '../../services/healthworkers.service';
import {Healthworker} from '../../models/healthworker';
import {Role} from '../../models/role';
import {RoleName} from '../../models/roleName';

@Component({
  selector: 'app-healthworkers',
  templateUrl: './healthworkers.component.html',
  styleUrls: ['./healthworkers.component.css']
})
export class HealthworkersComponent implements OnInit {
  healthWorkers: Healthworker[];

  constructor(
    private healthworkersService: HealthworkersService
  ) { }

  ngOnInit() {
    this.getHealthworkers();
  }

  getHealthworkers(): void {
    this.healthworkersService.getHealthworkers()
      .subscribe( healthworkers => this.healthWorkers = healthworkers);
  }

  approveHealthworker(hw: Healthworker): void {
    const mapper = {
      "approve": true
    };

    hw.roles = [new Role((new RoleName('HEALTHWORKER')))];

    this.healthworkersService.updateHealthworker(mapper, hw.id).
      subscribe();
  }
}
