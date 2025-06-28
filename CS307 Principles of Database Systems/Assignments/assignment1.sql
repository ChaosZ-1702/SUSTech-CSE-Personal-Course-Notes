select station_id, english_name, chinese_name from stations where lower(english_name) like '%east%' or lower(english_name) like '%west%' or lower(english_name) like '%north%' or lower(english_name) like '%south%' order by station_id;

select round(100.0 * count(case when district in ('Futian', 'Nanshan', 'Luohu') then 1 end) / count(*), 2) || '%' from stations;

select bus_line, count(*) as cnt from bus_lines group by bus_line having count(*) >= 20 order by cnt desc;

select ld.line_id, s.english_name from line_detail ld, stations s where ld.station_id = s.station_id and ld.num = 1 order by ld.line_id;

select count(distinct l.station_id) from stations s, line_detail l where l.station_id = s.station_id and s.district = 'Nanshan' and l.line_id != 1 and l.station_id not in (select station_id from line_detail where line_id = 1);

select line_id, count(*) as cnt from line_detail group by line_id having count(*) in (select max(num) as max_num from line_detail) order by line_id;

with maxnums as (select line_id, max(num) as max_num from line_detail where num >= 30 group by line_id)
select s.english_name, l.line_id, l.num from line_detail l, stations s where l.station_id = s.station_id and ((l.line_id, l.num) in (select * from maxnums) or (l.line_id in (select line_id from maxnums) and l.num = 1)) order by l.line_id, l.num;

select l.line_id, s.english_name, l.num from line_detail l, stations s where l.station_id = s.station_id and ((l.line_id, l.num - 1) in (select line_id, num from line_detail where station_id = 11) or (l.line_id, l.num + 1) in (select line_id, num from line_detail where station_id = 11)) order by l.line_id, s.station_id;