import {Answer} from "./answer-model";

export class Question{
  id: number | null = 0;
  germanQuestion: string = "eine deutsche frage";
  englishQuestion: string = "an english question";
  answers: Answer[] = [];
}
