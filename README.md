<h3><span style="color:orange">Приложение Filmorate</span></h3>
___

*Главная ветка <span style="color:green">main</span>.*<br>
___
*Добавлена ветка <span style="color:green">controllers-films-users</span>.*<br>
*Версия приложения:* <span style="color:yellow">**ver 1.0**</span> (Привести в соответствии с ТЗ спринта № 10).<br>
*Версия приложения:* <span style="color:yellow">**ver 1.1**</span> (Привести в соответствии с замечаниями).<br>
___
*Добавлена ветка <span style="color:green">add-friends-likes</span>.*<br>
*Версия приложения:* <span style="color:yellow">**ver 1.2**</span> (Привести в соответствии с ТЗ спринта № 11).<br>
*Версия приложения:* <span style="color:yellow">**ver 1.3**</span> (Привести в соответствии с замечаниями).<br>
___
*Добавлена ветка <span style="color:green">add-database</span>.*<br>
*Версия приложения:* <span style="color:yellow">**ver 1.4**</span> (Привести в соответствии с промежуточным заданием спринта № 12).<br>
*Диаграмма приложения Filmorate:*<br>
![Диаграмма приложения Filmorate](/diagramFilmorate.svg)

*Пояснения к диаграмме:*<br>
*- <span style="color:orange">film_storage</span> - таблица с фильмами, имеет первичный ключ - идентификатор (id_film), описание (description),*<br>
*продолжительность (duration), название (name), идентификатор рейтинга по классификации Ассоциации*<br> 
*кинокомпаний (id_rating_mpa) и дату релиза (release_date);*<br>
*- <span style="color:orange">film_genre</span> - таблица, связующая идентификатор фильмов с жанрами фильмов;*<br>
*- <span style="color:orange">genre</span> - таблица, содержащая виды жанров фильмов;*<br>
*- <span style="color:orange">likes</span> - таблица, связующая идентификатор фильмов с идентификаторами ползователей, что соответствует*<br>
*лайкам, поставленным пользователями, к каждому фильму;*<br>
*- <span style="color:orange">rating_mpa</span> - таблица, определяющая рейтинг по классификации Ассоциации кинокомпаний для каждого фильма;*<br>
*- <span style="color:orange">user_storage</span> - сущность пользователь, имеет имеет первичный ключ - идентификатор пользователя (id_user), дату рождения*<br>
*(birthday), электронную почту (email), логин (login) и имя (name);*<br>
*- <span style="color:orange">friends</span> - таблица, связующая идентификатор пользователя c идентификатором друга (пользователя) и определяющая*<br>
*статус их дружбы (friendship)*<br>

*Пример запросов в соответствии с диаграммой:*<br>

*1. Выбрать все фильмы определенного жанра:*<br>
*<span style="color:violet">SELECT</span> fs.name*<br>
*<span style="color:violet">FROM</span> film_storage <span style="color:violet">AS</span> fs*<br>
*<span style="color:violet">JOIN</span> film_genre <span style="color:violet">AS</span> fg <span style="color:violet">ON</span> fs.id_film = fg.id_film*<br>
*<span style="color:violet">JOIN</span> genre <span style="color:violet">AS</span> g <span style="color:violet">ON</span> film_genre.id_genre = genre.id_genre*<br>
*<span style="color:violet">WHERE</span> g.name = <span style="color:green">'Комедия'</span>;*<br>

*2. Подсчитать количество лайков, которые получил каждый фильм:*<br>
*<span style="color:violet">SELECT</span> fs.name,*<br>
&nbsp;&nbsp;&nbsp;&nbsp;*<span style="color:violet">COUNT</span>(l.id_user) <span style="color:violet">AS</span> like_count*<br>
*<span style="color:violet">FROM</span> film_storage <span style="color:violet">AS</span> fs*<br>
*<span style="color:violet">LEFT JOIN</span> likes <span style="color:violet">AS</span> l <span style="color:violet">ON</span> fs.id_film = l.id_film*<br>
*<span style="color:violet">GROUP BY</span> fs.name;*<br>

*3. Найти всех пользователей, которые поставили лайк определенному фильму:*<br>
*<span style="color:violet">SELECT</span> us.login*<br>
*<span style="color:violet">FROM</span> user_storage <span style="color:violet">AS</span> us*<br>
*<span style="color:violet">JOIN</span> likes <span style="color:violet">AS</span> l <span style="color:violet">ON</span> us.id_user = l.id_user*<br>
*<span style="color:violet">WHERE</span> l.id_film = 1;*<br>

*4. Перечислить все фильмы вместе с их рейтингом по классификации Ассоциации кинокомпаний:*<br>
*<span style="color:violet">SELECT</span> fs.name,*<br>
&nbsp;&nbsp;&nbsp;&nbsp;*rm.name <span style="color:violet">AS</span> mpa_rating*<br>
*<span style="color:violet">FROM</span> film_storage <span style="color:violet">AS</span> fs*<br>
*<span style="color:violet">JOIN</span> rating_mpa <span style="color:violet">AS</span> rm <span style="color:violet">ON</span> fs.id_rating_mpa = rm.id_rating_mpa;*<br>

___
Студент Шестаков А.В.
