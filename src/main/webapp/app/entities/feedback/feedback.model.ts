import { IFeedbackImage } from 'app/entities/feedback-image/feedback-image.model';
import { IMyUser } from 'app/entities/my-user/my-user.model';
import { IVoucher } from 'app/entities/voucher/voucher.model';

export interface IFeedback {
  id?: number;
  rate?: number;
  detail?: string | null;
  feedbackImages?: IFeedbackImage[] | null;
  user?: IMyUser | null;
  voucher?: IVoucher | null;
}

export class Feedback implements IFeedback {
  constructor(
    public id?: number,
    public rate?: number,
    public detail?: string | null,
    public feedbackImages?: IFeedbackImage[] | null,
    public user?: IMyUser | null,
    public voucher?: IVoucher | null
  ) {}
}

export function getFeedbackIdentifier(feedback: IFeedback): number | undefined {
  return feedback.id;
}
