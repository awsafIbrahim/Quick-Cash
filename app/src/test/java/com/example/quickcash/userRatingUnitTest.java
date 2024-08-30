package com.example.quickcash;

import static org.mockito.Mockito.*;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Button;
import android.widget.TextView;

import com.example.quickcash.objects.Employee;
import com.example.quickcash.ui_elements.EmployerViewPreferredEmployeesViewHolder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class userRatingUnitTest {

    @Mock private View mockView;
    @Mock private TextView mockUsernameTextView;
    @Mock private LinearLayout mockUserRatingWrapper;
    @Mock private RatingBar mockRatingBar;
    @Mock private Button mockSubmitRatingButton;
    @Mock private Employee mockEmployee;

    private EmployerViewPreferredEmployeesViewHolder viewHolder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(mockView.findViewById(R.id.usernameTextView)).thenReturn(mockUsernameTextView);
        when(mockView.findViewById(R.id.userRatingWrapper)).thenReturn(mockUserRatingWrapper);
        when(mockView.findViewById(R.id.userRatingBar)).thenReturn(mockRatingBar);
        when(mockView.findViewById(R.id.submitRatingButton)).thenReturn(mockSubmitRatingButton);
        viewHolder = new EmployerViewPreferredEmployeesViewHolder(mockView);
    }

    @Test
    public void testSetupRatingsSubmit_ShowsUserRatingWrapper() {
        viewHolder.setupRatingsSubmit(mockEmployee);
        verify(mockUserRatingWrapper).setVisibility(View.VISIBLE);
    }


}
