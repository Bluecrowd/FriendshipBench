import { Component, OnInit, AfterViewInit } from '@angular/core';
import {Bench} from '../../models/bench';
import {BenchesService} from '../../services/benches.service';

declare var $: any;

@Component({
  selector: 'app-benches',
  templateUrl: './benches.component.html',
  styleUrls: ['./benches.component.css']
})
export class BenchesComponent implements OnInit, AfterViewInit {
  benches: Bench[];

  public selectedBench: Bench;
  public addBenchToggle = false;

  constructor(
    private benchesService: BenchesService,
    ) {}

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
      .subscribe(benches => { this.benches = benches; setTimeout(() => { $('#dataTable').DataTable(); }, 350); });
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
