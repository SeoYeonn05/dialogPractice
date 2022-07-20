package kr.ac.tukorea.composepractice2

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Response

// textfield에 들어와 있는 데이터 저장
// nickname의 경우 변화가 감지될 경우 뷰모델에서 사용할 수 있는지 확인 후 true false 반환해 아이콘 색 변경해주기
// address의 경우 dialog에서 선택한 address를 클릭 시 textfield의 address에 자동으로 넣기
// dialog에서 address 검색 구성하기

class RegisterInformViewModel : ViewModel() {
    private val _nickName = MutableLiveData<String>()
    private val _address = MutableLiveData<String>()
    private val _dialogAddress = MutableLiveData<String>()
    val nickname: LiveData<String>
        get() = _nickName

    fun setAddress(textField: String): List<AddressDetail.HtReturnValue.Result.Zipcode> {
        val call = APiObject.retrofitService.getAddress(
            searchKeyword = textField
        )
        var addressDetail: List<AddressDetail.HtReturnValue.Result.Zipcode> = arrayListOf()

        call.enqueue(object : retrofit2.Callback<AddressDetail> {
            override fun onResponse(call: Call<AddressDetail>, response: Response<AddressDetail>) {
                if (response.isSuccessful) {
                    try {
                        addressDetail = response.body()!!.htReturnValue.result.zipcodes
                        Log.d("성공", "주소출력")
                    } catch (e: NullPointerException) {
                        Log.d("실패", e.message.toString())
                    }
                } else {
                    Log.d("실패", "에러")
                }
            }
            override fun onFailure(call: Call<AddressDetail>, t: Throwable) {
                Log.d("실패", t.message.toString())
            }
        })
        return addressDetail
        Log.d("확인", "주소 검색 작동")
    }

    private fun setClickList() {
        //        placeListView.setOnItemClickListener { adapterView, view, i, l ->
//            if (type == "start") {
//                startPlaceData = placeDetail[i]
//                binding.placeListView.visibility = View.GONE
//                binding.startPlace.setText(startPlaceData.title)
//
//
//            if (binding.startPlace.text.toString().isNotEmpty() && binding.endPlace.text.toString().isNotEmpty()) {
//                binding.buttonLayout.visibility = View.VISIBLE
//            }
//        }
    }
}
