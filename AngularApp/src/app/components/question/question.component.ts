import {Component, Input, OnInit} from '@angular/core';
import {Question} from '../../models/question';
import {QuestionsService} from '../../services/questions.service';
import {QuestionsComponent} from '../questions/questions.component';

@Component({
  selector: 'app-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {

  @Input() question: Question;

  constructor(
    private questionsService: QuestionsService,
    private location: Location,
    private questionsComponent: QuestionsComponent
  ) { }

  ngOnInit() {
  }

  close(): void {
    this.question = null;
    this.questionsComponent.selectedQuestion = null;
  }

}
