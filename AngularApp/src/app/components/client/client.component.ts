import { Component, OnInit } from '@angular/core';
import {ClientsService} from '../../services/clients.service';
import {ActivatedRoute} from '@angular/router';
import {Client} from '../../models/client';
import {Questionnaire} from '../../models/questionnaire';
import {QuestionnairesService} from '../../services/questionnaires.service';

declare var $: any;

@Component({
  selector: 'app-client',
  templateUrl: './client.component.html',
  styleUrls: ['./client.component.css']
})
export class ClientComponent implements OnInit {
  client: Client;

  questionnaires: Questionnaire[];

  constructor(
    private clientsService: ClientsService,
    private route: ActivatedRoute,
    private questionnairesService: QuestionnairesService
  ) { }

  ngOnInit() {
    this.getClient();
  }

  getClient(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.clientsService.getClient(id)
      .subscribe(client => {this.client = client; this.getQuestionnaires(); });
  }

  getQuestionnaires(): void {
    this.questionnairesService.getAllQuestionnairesMyclientMade(this.client.id)
      .subscribe(questionnaires => { this.questionnaires = questionnaires; setTimeout(() => { $('#dataTable').DataTable(); }, 350); });
  }

  setRedFlag(questionnaire: Questionnaire): void {
    this.questionnairesService.toggleRedFlag(questionnaire).subscribe(_ => this.refresh());
  }

  refresh(): void {
    this.questionnairesService.getAllQuestionnairesMyclientMade(this.client.id)
      .subscribe(questionnaires => { this.questionnaires = questionnaires; setTimeout(() => { $('#dataTable').DataTable(); }, 350); });
  }

}
