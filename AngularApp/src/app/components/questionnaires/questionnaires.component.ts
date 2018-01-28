import { Component, OnInit } from '@angular/core';
import {Questionnaire} from '../../models/questionnaire';
import {QuestionnairesService} from '../../services/questionnaires.service';

@Component({
  selector: 'app-questionnaires',
  templateUrl: './questionnaires.component.html',
  styleUrls: ['./questionnaires.component.css']
})
export class QuestionnairesComponent implements OnInit {
  questionnaires: Questionnaire[];

  constructor(
    private questionnairesService: QuestionnairesService,
  ) { }

  ngOnInit() {
    this.getQuestionnaires();
  }

  getQuestionnaires(): void {
    this.questionnairesService.getQuestionnaires()
      .subscribe(questionnaires => this.questionnaires = questionnaires);
  }

  toggleRedFlag(questionnaire: Questionnaire): void {
    this.questionnairesService.toggleRedFlag(questionnaire).subscribe();
  }

  delete(id: number): void {
  }

}
