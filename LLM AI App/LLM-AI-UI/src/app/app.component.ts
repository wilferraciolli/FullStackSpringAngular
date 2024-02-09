import { HttpClient } from '@angular/common/http';
import { Component, inject, OnInit, signal, WritableSignal } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {

  private _url: string = 'http://localhost:5001/api/chat';

  private _httpClient: HttpClient = inject(HttpClient);
  private _formBuilder: FormBuilder = inject(FormBuilder);

  public messageResponse: AIMessageResponse | null = null;
  public chatHistory: WritableSignal<ChatItem[]> = signal([]);

  public myForm = this._formBuilder.group({
    question: ['', [Validators.required]]
  });

  public ngOnInit(): void {
    this.chatHistory.set([{ user: 'bot', message: 'Hi, I am a bot. How can I help you today?' }]);
  }

  public onSubmit(): void {
    const questionToAsk: string | null | undefined = this.myForm?.value?.question;

    if (questionToAsk !== null && questionToAsk !== undefined) {
      this.chatHistory.mutate((current: ChatItem[]) => current.push({
        user: 'user',
        message: questionToAsk
      }));

      this.myForm?.reset();
      this._sendMessage(questionToAsk);
    }
  }

  private _sendMessage(message: string): void {
    console.log('got message ', message);

    this._httpClient.post<AIMessageResponse>(this._url, this._buildPayload(message)).subscribe(
      (response: AIMessageResponse) => this.messageResponse = response,
      error => this.chatHistory.mutate((current: ChatItem[]) => current.push({
        user: 'bot',
        message: error.message
      })));
  }

  private _buildPayload(message: string): ChatRequest {
    return {
      userMsg: message,
      newChatThread: true
    };
  }
}

export interface AIMessageResponse {
  message: string;
}

export interface ChatRequest {
  userMsg: string;
  newChatThread: boolean;
}

export interface ChatItem {
  user: 'user' | 'bot';
  message: string;
}

