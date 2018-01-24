export class Bench {
  id: number;
  streetname: string;
  housenumber: string;
  province: string;
  district: string;

  constructor(benchObject: any) {
    if (benchObject) {
      this.id = benchObject.id;
      this.streetname = benchObject.streetname;
      this.housenumber = benchObject.housenumber;
      this.province = benchObject.province;
      this.district = benchObject.district;
    }
  }
}
