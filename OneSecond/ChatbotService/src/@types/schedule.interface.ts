export default interface Scehdule {
  startDate: string;
  endDate: string;
  possibleDate:
    | [
        {
          userId: number;
          dateList: [string];
        }
      ]
    | null;
  isConfirmed: boolean;
}
