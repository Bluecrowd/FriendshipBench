import { Component, OnInit } from '@angular/core';
import {Question} from '../../models/question';
import {QuestionsService} from '../../services/questions.service';

@Component({
  selector: 'app-questions',
  templateUrl: './questions.component.html',
  styleUrls: ['./questions.component.css']
})
export class QuestionsComponent implements OnInit {
  questions: Question[];

  public addQuestionToggle = false;
  public selectedQuestion: Question;

  constructor(
    private questionsService: QuestionsService
  ) { }

  ngOnInit() {
    this.getQuestions();
  }

  toggleAddQuestion(): void {
    this.addQuestionToggle = !this.addQuestionToggle;
  }

  toggleQuestion(question: Question): void {
    this.selectedQuestion = question;
  }

  toggleActive(question: Question): void {
    question.active = !question.active;
    this.questionsService.updateQuestion(question).subscribe();
  }

  getQuestions(): void {
    this.questionsService.getQuestions()
      .subscribe( questions => this.questions = questions);
  }
}
