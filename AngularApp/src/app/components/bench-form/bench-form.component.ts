import {Component, Input, OnInit} from '@angular/core';

import { Bench } from '../../models/bench';
import { BenchesComponent } from '../benches/benches.component';
import { BenchesService} from '../../services/benches.service';

@Component({
  selector: 'app-bench-form',
  templateUrl: './bench-form.component.html',
  styleUrls: ['./bench-form.component.css']
})
export class BenchFormComponent implements OnInit {
  @Input() addBenchToggle: false;

  submitted = false;

  model: Object = {};

  onSubmit() { this.submitted = true; }

  // TODO: Remove this when it works
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(
    private benchesComponent: BenchesComponent,
    private benchesService: BenchesService,
  ) {  }

  ngOnInit() {
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
