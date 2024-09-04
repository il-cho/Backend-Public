export default interface Invitation {
  invitationCode: string;
  title: string;
  description: string;
  inviter: string;
  confirmedDate: string | null;
  attendees: {
    userId: [number];
  } | null;
}
