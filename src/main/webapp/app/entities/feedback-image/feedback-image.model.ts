import { IFeedback } from 'app/entities/feedback/feedback.model';

export interface IFeedbackImage {
  id?: number;
  content?: string;
  feedback?: IFeedback | null;
}

export class FeedbackImage implements IFeedbackImage {
  constructor(public id?: number, public content?: string, public feedback?: IFeedback | null) {}
}

export function getFeedbackImageIdentifier(feedbackImage: IFeedbackImage): number | undefined {
  return feedbackImage.id;
}
