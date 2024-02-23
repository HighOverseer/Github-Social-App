package com.fajar.githubuserappdicoding.domain

import android.content.Context
import com.fajar.githubuserappdicoding.core.domain.common.DynamicString
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.mock


class StringResTest {

    private val dummyStringVal = "Test"

    @Mock
    private lateinit var context: Context

    @Before
    fun before() {
        context = mock(Context::class.java)
    }

    @Test
    fun `When Get Value Should Return Its Literal Given String Value`() {
        val dynamicString = DynamicString(dummyStringVal)
        val stringVal = dynamicString.getValue(context)
        assertEquals(dummyStringVal, stringVal)
    }

}