package org.zerock.mreview.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Movie;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("select m, mi, avg(coalesce(r.grade,0)), count(distinct r) from Movie m " +
            " left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review r on r.movie = m group by m")
    Page<Object[]> getListPage(Pageable pageable);

    @Query("select m, mi, avg(coalesce(r.grade,0)), count(r) " +
            " from Movie m " +
            " left outer join MovieImage mi on mi.movie = m " +
            " left outer join Review r on r.movie = m " +
            " where  m.mno = :mno group by mi")
    List<Object[]> getMovieWithAll(Long mno);

    // Hibernate:
    //    select
    //        movie0_.mno as col_0_0_,
    //        movieimage1_.inum as col_1_0_,
    //        avg(coalesce(review2_.grade,
    //        0)) as col_2_0_,
    //        count(review2_.reviewnum) as col_3_0_,
    //        movie0_.mno as mno1_1_0_,
    //        movieimage1_.inum as inum1_2_1_,
    //        movie0_.moddate as moddate2_1_0_,
    //        movie0_.regdate as regdate3_1_0_,
    //        movie0_.title as title4_1_0_,
    //        movieimage1_.img_name as img_name2_2_1_,
    //        movieimage1_.movie_mno as movie_mn5_2_1_,
    //        movieimage1_.path as path3_2_1_,
    //        movieimage1_.uuid as uuid4_2_1_
    //    from
    //        movie movie0_
    //    left outer join
    //        movie_image movieimage1_
    //            on (
    //                movieimage1_.movie_mno=movie0_.mno
    //            )
    //    left outer join
    //        review review2_
    //            on (
    //                review2_.movie_mno=movie0_.mno
    //            )
    //    where
    //        movie0_.mno=?
    //    group by
    //        movieimage1_.inum
}
