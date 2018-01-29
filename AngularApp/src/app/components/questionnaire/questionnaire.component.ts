import {Component, Input, OnInit} from '@angular/core';
import {Questionnaire} from '../../models/questionnaire';
import {ActivatedRoute} from '@angular/router';
import {QuestionnairesService} from '../../services/questionnaires.service';

@Component({
  selector: 'app-questionnaire',
  templateUrl: './questionnaire.component.html',
  styleUrls: ['./questionnaire.component.css']
})
export class QuestionnaireComponent implements OnInit {
  @Input() questionnaire: Questionnaire;

  constructor(
    private route: ActivatedRoute,
    private questionnairesService: QuestionnairesService,
  ) { }

  ngOnInit() {
    this.getQuestionnaire();
  }

  getQuestionnaire(): void {
    const id = +this.route.snapshot.paramMap.get('id');
    this.questionnairesService.getQuestionnaire(id)
      .subscribe(questionnaire => this.questionnaire = questionnaire);
  }

}
