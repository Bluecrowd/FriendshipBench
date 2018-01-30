import { Component, OnInit } from '@angular/core';
import {Healthworker} from '../../models/healthworker';
import {HealthworkersService} from '../../services/healthworkers.service';
import {ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-healthworker',
  templateUrl: './healthworker.component.html',
  styleUrls: ['./healthworker.component.css']
})
export class HealthworkerComponent implements OnInit {

  healthworker: Healthworker;

  editToggle = false;

  model: Object = {};

  // TODO: Remove this when it works
  diagnostic(): string { return JSON.stringify(this.model); }

  constructor(
    private healthworkersService: HealthworkersService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.getHealthworker();
  }

  toggleEdit(): void {
    this.editToggle = !this.editToggle;
  }

  save(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.healthworkersService.editHw(this.model, id)
      .subscribe(_ => {this.getHealthworker(); this.editToggle = false; this.diagnostic(); });
  }

  getHealthworker(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.healthworkersService.getHealthworker(id)
      .subscribe(healthworker => {this.healthworker = healthworker; this.setModel(healthworker); });
  }

  setModel(hw: Healthworker): void {
    this.model = {
      'firstName': hw.firstName,
      'lastName': hw.lastName,
      'gender': hw.gender
    };
  }


}
