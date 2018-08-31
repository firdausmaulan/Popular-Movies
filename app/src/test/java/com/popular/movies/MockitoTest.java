package com.popular.movies;

import com.popular.movies.mainPage.MainContract;
import com.popular.movies.mainPage.MainPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MockitoTest {

    @Mock
    MainContract.View view;

    private MainPresenter presenter;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenter();
    }

    @Test
    public void loadPopularMovies(){
        presenter.loadPopularMovies(1);
    }

}
