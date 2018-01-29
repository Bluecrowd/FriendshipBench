import {Component, Input, OnInit} from '@angular/core';
import {QuestionsComponent} from '../questions/questions.component';
import {QuestionsService} from '../../services/questions.service';
import {Bench} from '../../models/bench';
import {Question} from '../../models/question';

@Component({
  selector: 'app-question-form',
  templateUrl: './question-form.component.html',
  styleUrls: ['./question-form.component.css']
})
export class QuestionFormComponent implements OnInit {
  @Input() addQuestionToggle: false;

  model: Object = {};

  states = [true, false];

  // TODO: Remove this when it works
  get diagnostic() { return JSON.stringify(this.model); }

  constructor(
    private questionsComponent: QuestionsComponent,
    private questionsService: QuestionsService,
  ) { }ng

  ngOnInit() {
  }

  add(): void {
    if (this.model != null) {
      this.questionsService.addQuestion(this.model as Question)
        .subscribe(question => {this.questionsComponent.questions.push(question);
        });
    }
  }

  closeAddQuestion(): void {
    this.addQuestionToggle = false;
    this.questionsComponent.addQuestionToggle = false;
    this.model = {};
  }

}
