/*
 * Copyright (c) 2010-2023 Belledonne Communications SARL.
 *
 * This file is part of linphone-android
 * (see https://www.linphone.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.muktinath.mphone.ui.call.conference.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.muktinath.mphone.LinphoneApplication.Companion.coreContext
import com.muktinath.mphone.R
import org.linphone.core.tools.Log
import com.muktinath.mphone.databinding.CallConferenceActiveSpeakerFragmentBinding
import com.muktinath.mphone.ui.call.conference.viewmodel.ConferenceViewModel
import com.muktinath.mphone.ui.call.fragment.GenericCallFragment
import com.muktinath.mphone.ui.call.viewmodel.CurrentCallViewModel

@UiThread
class ConferenceActiveSpeakerFragment : GenericCallFragment() {
    companion object {
        private const val TAG = "[Conference Active Speaker Fragment]"
    }

    private lateinit var binding: CallConferenceActiveSpeakerFragmentBinding

    private lateinit var callViewModel: CurrentCallViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CallConferenceActiveSpeakerFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        callViewModel = requireActivity().run {
            ViewModelProvider(this)[CurrentCallViewModel::class.java]
        }

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = callViewModel
        binding.conferenceViewModel = callViewModel.conferenceModel

        callViewModel.conferenceModel.conferenceLayout.observe(viewLifecycleOwner) {
            when (it) {
                ConferenceViewModel.GRID_LAYOUT -> {
                    Log.i(
                        "$TAG Conference layout changed to mosaic, navigating to matching fragment"
                    )
                    if (findNavController().currentDestination?.id == R.id.conferenceActiveSpeakerFragment) {
                        findNavController().navigate(
                            R.id.action_conferenceActiveSpeakerFragment_to_conferenceGridFragment
                        )
                    }
                }
                ConferenceViewModel.AUDIO_ONLY_LAYOUT -> {
                    Log.i(
                        "$TAG Conference layout changed to audio only, navigating to matching fragment"
                    )
                    if (findNavController().currentDestination?.id == R.id.conferenceActiveSpeakerFragment) {
                        findNavController().navigate(
                            R.id.action_conferenceActiveSpeakerFragment_to_conferenceAudioOnlyFragment
                        )
                    }
                }
                else -> {
                }
            }
        }

        coreContext.postOnCoreThread { core ->
            Log.i("$TAG Setting native video window ID")
            core.nativeVideoWindowId = binding.activeSpeakerSurface
        }
    }
}
