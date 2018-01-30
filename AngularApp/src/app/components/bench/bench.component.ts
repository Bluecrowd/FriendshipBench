import {Component, Input, OnInit} from '@angular/core';
import {BenchesService} from '../../services/benches.service';
import {Bench} from '../../models/bench';
import {ActivatedRoute} from '@angular/router';
import { Location} from '@angular/common';
import {BenchesComponent} from '../benches/benches.component';

@Component({
  selector: 'app-bench',
  templateUrl: './bench.component.html',
  styleUrls: ['./bench.component.css']
})
export class BenchComponent implements OnInit {

  @Input() bench: Bench;

  constructor(
    private benchesService: BenchesService,
    private route: ActivatedRoute,
    private location: Location,
    private benchesComponent: BenchesComponent
  ) { }

  ngOnInit() {
  }

  save(): void {
    this.benchesService.updateBench(this.bench).subscribe();
    this.benchesComponent.selectedBench = null;
  }

  close(): void {
    this.bench = null;
    this.benchesComponent.selectedBench = null;
  }

  goBack(): void {
    this.location.back();
  }

}
