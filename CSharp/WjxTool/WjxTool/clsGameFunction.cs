using System;
using System.Collections.Generic;
using System.Text;

namespace WjxTool
{

  //      <GameFunction Name="Xem tích lũy nạp thẻ tháng này" Key="CheckMonthPay" Type="Condition" Description="Tham số 1:Yêu cầu tích lũy nạp thẻ tháng này đạt trị yêu cầu (đơn vị tiền)
  //Tham số 2:Thông tin nhắc nhở  
  //Ví dụ: CheckMonthPay:100,&quot;Tích lũy nạp thẻ tháng này cần đạt 100 đồng&quot; (phán đoán tích lũy nạp thẻ có đạt điều kiện yêu cầu không)">
  //      <Parameter Name="Mức tích lũy nạp thẻ tháng này" Description="Đơn vị là đồng" Regex="^[1-9]+[0-9]*$" ErrorMessage="Phải nhập số nguyên"/>
  //      <Parameter Name="Thông tin nhắc nhở" Description="Nếu chưa đạt đủ tích lũy nạp thẻ tháng này sẽ hiển thị nhắc nhở" Regex="^[^|]*$" ErrorMessage="Cấm nhập ký tự '|'"/>
  //  </GameFunction>

  //  <GameFunction Name="Xem tích lũy nạp thẻ tháng này" Key="CheckMonthPay_VN" Type="Condition" Description="Tham số 1: Yêu cầu mức thẻ nạp trong tháng (đơn vị VND)
  //Tham số 2: Thông tin nhắc nhở  
  //Ví dụ: CheckMonthPay:100, &quot;Tích lũy thẻ nạp trong tháng phải đạt 100 VND&quot; (Phán đoán tích lũy nạp thẻ có đạt yêu cầu không)">
  //      <Parameter Name="Mức tích lũy nạp thẻ tháng này" Description="Đơn vị VND" Regex="^[1-9]+[0-9]*$" ErrorMessage="Phải nhập số nguyên"/>
  //      <Parameter Name="Thông tin nhắc nhở" Description="Nếu chưa đạt đủ tích lũy nạp thẻ tháng này sẽ hiển thị nhắc nhở" Regex="^[^|]*$" ErrorMessage="Cấm nhập ký tự '|'"/>
  //  </GameFunction>


    public class clsGameFunction
    {
        private string key;
        private string name;      
        private string type;
        private string description;

        private List<clsParam> cParma;


        public string Name
        {
            get { return name; }
            set { name = value; }
        }

        public List<clsParam> CParma
        {
            get { return cParma; }
            set { cParma = value; }
        }

       

        public string Description
        {
            get { return description; }
            set { description = value; }
        }

        public string Type
        {
            get { return type; }
            set { type = value; }
        }

        public string Key
        {
            get { return key; }
            set { key = value; }
        }
    }
}
