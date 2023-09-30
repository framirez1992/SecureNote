package com.far.securenote.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.far.securenote.R
import com.far.securenote.common.ScreenNavigator
import com.far.securenote.databinding.FragmentMainScreenBinding
import com.far.securenote.model.services.NoteService
import com.far.securenote.model.OperationResult
import com.far.securenote.view.adapters.NoteAdapter
import com.far.securenote.view.common.BaseFragment
import com.far.securenote.viewmodel.MainScreenViewModel
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainScreen : BaseFragment() {

    private lateinit var _binding:FragmentMainScreenBinding
    private lateinit var viewModel:MainScreenViewModel

    @Inject lateinit var noteService: NoteService
    @Inject lateinit var screenNavigator: ScreenNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presentationComponent.inject(this)
        viewModel = ViewModelProvider(this, MainScreenViewModel.MainScreenViewModelFactory(noteService))[MainScreenViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainScreenBinding.inflate(inflater,container,false)
        return  _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
        initObservers()
        viewModel.search("")

    }


    private fun initViews(){
        _binding.btnSearch.setOnClickListener {
            viewModel.search(_binding.etSearch.text.toString())
        }
        _binding.btnAddNote.setOnClickListener {
            screenNavigator.Notes(R.id.frame,null)
        }
        _binding.rv.layoutManager = LinearLayoutManager(context)

    }

    private fun initObservers(){
        viewModel.state.observe(this){ it ->
            _binding.btnSearch.setLoading(it.loading)
            _binding.etSearch.setText(it.search)
            _binding.rv.adapter = NoteAdapter(it.notes){note->
                screenNavigator.Notes(R.id.frame,note)
            }

            enableViews(!it.loading)
            processOperationResul(it.operationResult)
        }
    }

    private fun enableViews(enable:Boolean){
        _binding.btnSearch.isEnabled = enable
        _binding.etSearch.isEnabled = enable
        _binding.rv.isEnabled = enable
    }

    private fun processOperationResul(operation: OperationResult?){
        if(operation != null){
            if (operation.code == "01" || operation.code == "99") {//only errors and timeouts
                Toast.makeText(context,operation.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(/*param1: String, param2: String*/) =
            MainScreen().apply {
              /*  arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }*/
            }
    }
}