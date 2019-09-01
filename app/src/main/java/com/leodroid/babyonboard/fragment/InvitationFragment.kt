package com.leodroid.babyonboard.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leodroid.babyonboard.R
import com.leodroid.babyonboard.databinding.FragmentInvitationBinding

class InvitationFragment: Fragment() {

    private lateinit var binding: FragmentInvitationBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invitation, container, false)
        return binding.root
    }
}