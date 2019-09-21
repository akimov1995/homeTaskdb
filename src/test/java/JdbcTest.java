import dao.AlbumDao;
import dao.ArtistDao;
import dao.TrackDao;
import functional.FunctionalClass;
import model.Album;
import model.Artist;
import model.Track;
import org.junit.*;
import org.junit.rules.ExpectedException;
import utils.JdbcUtils;

import java.sql.SQLException;
import java.util.Map;

import static org.junit.Assert.*;

public class JdbcTest {
    private ArtistDao artistDao;
    private AlbumDao albumDao;
    private TrackDao trackDao;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void initDb(){
        JdbcUtils.createDataBase();
        JdbcUtils.createTables();
    }

    @Before
    public void initDao(){
        artistDao = new ArtistDao();
        albumDao = new AlbumDao();
        trackDao = new TrackDao();
    }

    @Test
    public void testProcedure() {
        String procedureResult = FunctionalClass.callProcedure();
        assertEquals("mmlp",procedureResult);
    }

    @Test
    public void testTransaction() {
        FunctionalClass.createTransaction();
        assertEquals(50001000,albumDao.getAlbumById(2).getCopiesCount());
        assertEquals(2200000,artistDao.getArtistById(1).getSalary());
    }

    @Test
    public void testSelectQuery(){
        Map<String,Integer> map = FunctionalClass.selectQuery();
        Map.Entry<String, Integer> e = map.entrySet().iterator().next();
        String name = e.getKey();
        int count = e.getValue();

        assertEquals(name,"Eminem");
        assertEquals(3, count);
    }

    @Test
    public void testTrigger() throws Exception {
        exception.expect(SQLException.class);
        artistDao.deleteArtist(1);
    }

    @Test
    public void testArtistDao() throws Exception {
        Artist artist = new Artist();
        artist.setArtistId(6);
        artist.setName("new artist");
        artist.setSalary(10);
        artistDao.addArtist(artist);

        assertEquals("new artist", artistDao.getArtistById(6).getName());
        assertEquals(10, artistDao.getArtistById(6).getSalary());

        artist.setName("artist");
        artistDao.updateArtist(artist);
        assertEquals("artist", artistDao.getArtistById(6).getName());

        artistDao.deleteArtist(6);
        assertNull(artistDao.getArtistById(6));
    }

    @Test
    public void testAlbumDao() {
        Album album = new Album();
        album.setId(6);
        album.setName("some album");
        album.setGenre("jazz");
        album.setArtistId(5);
        albumDao.addAlbum(album);

        assertEquals("some album", albumDao.getAlbumById(6).getName());
        assertEquals("jazz", albumDao.getAlbumById(6).getGenre());

        album.setName("album");
        albumDao.updateAlbum(album);
        assertEquals("album", albumDao.getAlbumById(6).getName());

        albumDao.deleteAlbum(6);
        assertNull(albumDao.getAlbumById(6));
    }

    @Test
    public void testTrackDao() {
        Track track = new Track();
        track.setId(6);
        track.setName("some track");
        track.setProducerName("producer");
        track.setAlbumId(5);
        trackDao.addTrack(track);

        assertEquals("some track", trackDao.getTrackById(6).getName());
        assertEquals("producer", trackDao.getTrackById(6).getProducerName());
        assertEquals(5,trackDao.getTrackById(6).getAlbumId().intValue());

        track.setName("other name");
        trackDao.updateTrack(track);
        assertEquals("other name", trackDao.getTrackById(6).getName());

        trackDao.deleteTrack(6);
        assertNull(trackDao.getTrackById(6));
    }

    @Test
    public void testSelectQuery2(){
        Map<String, String> map = FunctionalClass.selectQuery2();
        Map.Entry<String, String> e = map.entrySet().iterator().next();
        String albumName = e.getKey();
        String trackName = e.getValue();

        assertNull(albumName);
        assertEquals(trackName,"track");
    }

    @AfterClass
    public static void closeDb(){
        JdbcUtils.dropDataBase();
    }
}