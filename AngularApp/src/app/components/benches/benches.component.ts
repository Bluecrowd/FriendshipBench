import { Component, OnInit } from '@angular/core';
import {Bench} from '../../models/bench';
import {BenchesService} from '../../services/benches.service';

@Component({
  selector: 'app-benches',
  templateUrl: './benches.component.html',
  styleUrls: ['./benches.component.css']
})
export class BenchesComponent implements OnInit {
  benches: Bench[];

  public selectedBench: Bench;
  public addBenchToggle = false;

  constructor(
    private benchesService: BenchesService,
    ) { }

  ngOnInit() {
    this.getBenches();
  }

  toggleAddBench(): void {
    this.addBenchToggle = !this.addBenchToggle;
  }

  getBenches(): void {
    this.benchesService.getBenches()
      .subscribe(benches => this.benches = benches);
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
