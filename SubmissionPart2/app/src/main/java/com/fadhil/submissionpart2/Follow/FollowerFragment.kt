package com.fadhil.submissionpart2.Follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fadhil.submissionpart2.R
import com.fadhil.submissionpart2.User.UserActivityDetail
import com.fadhil.submissionpart2.User.UserAdapter
import com.fadhil.submissionpart2.databinding.ActivityFollowerFragmentBinding


class FollowerFragment : Fragment(R.layout.activity_follower_fragment) {
    private var _binding : ActivityFollowerFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FollowerViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ActivityFollowerFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val args = arguments
        username = args?.getString(UserActivityDetail.EXTRA_USERNAME).toString()
        _binding = ActivityFollowerFragmentBinding.bind(view)


        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            listusers.layoutManager = LinearLayoutManager(activity)
            listusers.setHasFixedSize(true)
            listusers.adapter = adapter
        }

        showLoading (true)
        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowerViewModel::class.java)
        viewModel.setFollowers(username)
        viewModel.getFollower().observe(viewLifecycleOwner,{
            if (it!=null){
                adapter.setList(it)
                showLoading(false)

            }
        })



    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state: Boolean) { binding.bars.visibility = if (state) View.VISIBLE else View.GONE }
}