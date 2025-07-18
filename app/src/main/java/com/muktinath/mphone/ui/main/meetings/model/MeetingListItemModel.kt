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
package com.muktinath.mphone.ui.main.meetings.model

import androidx.annotation.WorkerThread
import com.muktinath.mphone.utils.TimestampUtils

class MeetingListItemModel
    @WorkerThread
    constructor(
    meetingModel: MeetingModel?,
    val firstMeetingOfTheWeek: Boolean
) {
    val isToday = meetingModel == null || meetingModel.isToday

    val isTodayIndicator = meetingModel == null

    val month = meetingModel?.month ?: TimestampUtils.month(System.currentTimeMillis(), false)

    val day = meetingModel?.day ?: TimestampUtils.dayOfWeek(System.currentTimeMillis(), false)

    val dayNumber = meetingModel?.dayNumber ?: TimestampUtils.dayOfMonth(
        System.currentTimeMillis(),
        false
    )

    val weekLabel = meetingModel?.weekLabel ?: TimestampUtils.firstAndLastDayOfWeek(
        System.currentTimeMillis(),
        false
    )

    val model = meetingModel ?: TodayModel()

    init {
        meetingModel?.firstMeetingOfTheWeek?.postValue(firstMeetingOfTheWeek)
    }

    class TodayModel
}
